CREATE OR REPLACE FUNCTION maps.get_land_report_summary(payload jsonb DEFAULT '{}'::jsonb)
RETURNS jsonb
LANGUAGE sql
AS $$
WITH filtered AS (
    SELECT *
    FROM maps.preby_lands pl
    WHERE (COALESCE(payload->>'presbytery', '') = '' OR pl.pl_presbytry = payload->>'presbytery')
      AND (COALESCE(payload->>'district_id', '') = '' OR pl.district_id = NULLIF(payload->>'district_id', '')::bigint)
      AND (COALESCE(payload->>'district', '') = '' OR pl.pl_district = payload->>'district')
      AND (COALESCE(payload->>'congregation_id', '') = '' OR pl.congregation_id = NULLIF(payload->>'congregation_id', '')::bigint)
      AND (COALESCE(payload->>'congregation', '') = '' OR pl.pl_congregation = payload->>'congregation')
      AND (COALESCE(payload->>'registered', '') = '' OR UPPER(COALESCE(pl.pl_is_land_registered, '')) = UPPER(payload->>'registered'))
      AND (COALESCE(payload->>'hasDocuments', '') = '' OR UPPER(COALESCE(pl.pl_has_any_documents, '')) = UPPER(payload->>'hasDocuments'))
      AND (COALESCE(payload->>'hasDispute', '') = '' OR UPPER(COALESCE(pl.pl_is_dispute_on_land, '')) = UPPER(payload->>'hasDispute'))
), expiring AS (
    SELECT COUNT(*) AS expiring_count
    FROM filtered
    WHERE date_of_expiration IS NOT NULL
      AND date_of_expiration::date >= CURRENT_DATE
      AND (
            COALESCE(payload->>'expiringWithinDays', '') = ''
            OR date_of_expiration::date <= CURRENT_DATE + NULLIF(payload->>'expiringWithinDays', '')::integer
          )
)
SELECT jsonb_build_object(
    'totalRecords', COUNT(*),
    'totalPresbyteries', COUNT(DISTINCT NULLIF(pl_presbytry, '')),
    'totalDistricts', COUNT(DISTINCT NULLIF(pl_district, '')),
    'totalCongregations', COUNT(DISTINCT NULLIF(pl_congregation, '')),
    'totalAcreage', COALESCE(ROUND(SUM(COALESCE(pl_land_size_acre, 0)), 3), 0),
    'totalHectares', COALESCE(ROUND(SUM(COALESCE(pl_land_size_hectare, 0)), 3), 0),
    'registeredCount', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_is_land_registered, '')) = 'YES'),
    'documentCount', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_has_any_documents, '')) = 'YES'),
    'disputeCount', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_is_dispute_on_land, '')) = 'YES'),
    'expiringCount', (SELECT expiring_count FROM expiring)
)
FROM filtered;
$$;

CREATE OR REPLACE FUNCTION maps.get_land_report_breakdowns(payload jsonb DEFAULT '{}'::jsonb)
RETURNS jsonb
LANGUAGE sql
AS $$
WITH filtered AS (
    SELECT *
    FROM maps.preby_lands pl
    WHERE (COALESCE(payload->>'presbytery', '') = '' OR pl.pl_presbytry = payload->>'presbytery')
      AND (COALESCE(payload->>'district_id', '') = '' OR pl.district_id = NULLIF(payload->>'district_id', '')::bigint)
      AND (COALESCE(payload->>'district', '') = '' OR pl.pl_district = payload->>'district')
      AND (COALESCE(payload->>'congregation_id', '') = '' OR pl.congregation_id = NULLIF(payload->>'congregation_id', '')::bigint)
      AND (COALESCE(payload->>'congregation', '') = '' OR pl.pl_congregation = payload->>'congregation')
      AND (COALESCE(payload->>'registered', '') = '' OR UPPER(COALESCE(pl.pl_is_land_registered, '')) = UPPER(payload->>'registered'))
      AND (COALESCE(payload->>'hasDocuments', '') = '' OR UPPER(COALESCE(pl.pl_has_any_documents, '')) = UPPER(payload->>'hasDocuments'))
      AND (COALESCE(payload->>'hasDispute', '') = '' OR UPPER(COALESCE(pl.pl_is_dispute_on_land, '')) = UPPER(payload->>'hasDispute'))
),
grouped_presbytery AS (
    SELECT
        COALESCE(NULLIF(pl_presbytry, ''), 'Unspecified') AS label,
        COUNT(*) AS record_count,
        ROUND(SUM(COALESCE(pl_land_size_acre, 0)), 3) AS total_acreage,
        COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_is_land_registered, '')) = 'YES') AS registered_count
    FROM filtered
    GROUP BY 1
    ORDER BY COUNT(*) DESC, 1
),
grouped_development AS (
    SELECT
        COALESCE(NULLIF(pl_development_status, ''), 'Unspecified') AS label,
        COUNT(*) AS record_count,
        ROUND(SUM(COALESCE(pl_land_size_acre, 0)), 3) AS total_acreage
    FROM filtered
    GROUP BY 1
    ORDER BY COUNT(*) DESC, 1
),
grouped_ownership AS (
    SELECT
        COALESCE(NULLIF(pl_type_of_ownership, ''), 'Unspecified') AS label,
        COUNT(*) AS record_count
    FROM filtered
    GROUP BY 1
    ORDER BY COUNT(*) DESC, 1
),
grouped_registration AS (
    SELECT
        CASE
            WHEN UPPER(COALESCE(pl_is_land_registered, '')) = 'YES' AND COALESCE(NULLIF(pl_status_of_registration, ''), '') <> ''
                THEN pl_status_of_registration
            WHEN UPPER(COALESCE(pl_is_land_registered, '')) = 'YES'
                THEN 'Registered'
            WHEN UPPER(COALESCE(pl_is_land_registered, '')) = 'NO'
                THEN 'Not Registered'
            ELSE 'Unspecified'
        END AS label,
        COUNT(*) AS record_count
    FROM filtered
    GROUP BY 1
    ORDER BY COUNT(*) DESC, 1
),
grouped_compliance AS (
    SELECT 'Has Documents' AS label, COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_has_any_documents, '')) = 'YES') AS record_count
    FROM filtered
    UNION ALL
    SELECT 'No Documents', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_has_any_documents, '')) = 'NO')
    FROM filtered
    UNION ALL
    SELECT 'Under Dispute', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_is_dispute_on_land, '')) = 'YES')
    FROM filtered
    UNION ALL
    SELECT 'No Dispute', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_is_dispute_on_land, '')) = 'NO')
    FROM filtered
)
SELECT jsonb_build_object(
    'locationBreakdowns', jsonb_build_object(
        'byPresbytery', COALESCE((
            SELECT jsonb_agg(jsonb_build_object(
                'label', label,
                'recordCount', record_count,
                'totalAcreage', total_acreage,
                'registeredCount', registered_count
            ))
            FROM grouped_presbytery
        ), '[]'::jsonb)
    ),
    'statusBreakdowns', jsonb_build_object(
        'developmentStatus', COALESCE((
            SELECT jsonb_agg(jsonb_build_object(
                'label', label,
                'recordCount', record_count,
                'totalAcreage', total_acreage
            ))
            FROM grouped_development
        ), '[]'::jsonb),
        'ownershipType', COALESCE((
            SELECT jsonb_agg(jsonb_build_object(
                'label', label,
                'recordCount', record_count
            ))
            FROM grouped_ownership
        ), '[]'::jsonb),
        'registrationStatus', COALESCE((
            SELECT jsonb_agg(jsonb_build_object(
                'label', label,
                'recordCount', record_count
            ))
            FROM grouped_registration
        ), '[]'::jsonb),
        'complianceSnapshot', COALESCE((
            SELECT jsonb_agg(jsonb_build_object(
                'label', label,
                'recordCount', record_count
            ))
            FROM grouped_compliance
        ), '[]'::jsonb)
    )
);
$$;

CREATE OR REPLACE FUNCTION maps.get_land_report_expiring_leases(payload jsonb DEFAULT '{}'::jsonb)
RETURNS jsonb
LANGUAGE sql
AS $$
WITH filtered AS (
    SELECT *
    FROM maps.preby_lands pl
    WHERE (COALESCE(payload->>'presbytery', '') = '' OR pl.pl_presbytry = payload->>'presbytery')
      AND (COALESCE(payload->>'district_id', '') = '' OR pl.district_id = NULLIF(payload->>'district_id', '')::bigint)
      AND (COALESCE(payload->>'district', '') = '' OR pl.pl_district = payload->>'district')
      AND (COALESCE(payload->>'congregation_id', '') = '' OR pl.congregation_id = NULLIF(payload->>'congregation_id', '')::bigint)
      AND (COALESCE(payload->>'congregation', '') = '' OR pl.pl_congregation = payload->>'congregation')
      AND (COALESCE(payload->>'registered', '') = '' OR UPPER(COALESCE(pl.pl_is_land_registered, '')) = UPPER(payload->>'registered'))
      AND (COALESCE(payload->>'hasDocuments', '') = '' OR UPPER(COALESCE(pl.pl_has_any_documents, '')) = UPPER(payload->>'hasDocuments'))
      AND (COALESCE(payload->>'hasDispute', '') = '' OR UPPER(COALESCE(pl.pl_is_dispute_on_land, '')) = UPPER(payload->>'hasDispute'))
)
SELECT COALESCE(jsonb_agg(jsonb_build_object(
    'plId', pl_id,
    'glpin', pl_glpin,
    'presbytery', pl_presbytry,
    'district', pl_district,
    'congregation', pl_congregation,
    'locality', pl_locality,
    'expirationDate', date_of_expiration,
    'daysToExpiration', (date_of_expiration::date - CURRENT_DATE),
    'landSizeAcre', pl_land_size_acre,
    'landSizeHectare', pl_land_size_hectare,
    'registrationStatus', pl_status_of_registration,
    'typeOfInterest', pl_type_of_interest,
    'typeOfUse', pl_type_of_use
) ORDER BY date_of_expiration ASC NULLS LAST), '[]'::jsonb)
FROM filtered
WHERE date_of_expiration IS NOT NULL
  AND date_of_expiration::date >= CURRENT_DATE
  AND (
        COALESCE(payload->>'expiringWithinDays', '') = ''
        OR date_of_expiration::date <= CURRENT_DATE + NULLIF(payload->>'expiringWithinDays', '')::integer
      );
$$;

CREATE OR REPLACE FUNCTION maps.get_land_report_dashboard(payload jsonb DEFAULT '{}'::jsonb)
RETURNS jsonb
LANGUAGE sql
AS $$
SELECT jsonb_build_object(
    'success', true,
    'filters', COALESCE(payload, '{}'::jsonb),
    'summary', maps.get_land_report_summary(payload),
    'filterOptions', jsonb_build_object(
        'presbyteries', COALESCE((
            SELECT jsonb_agg(presbytery_name ORDER BY presbytery_name)
            FROM (
                SELECT DISTINCT pl_presbytry AS presbytery_name
                FROM maps.preby_lands
                WHERE COALESCE(NULLIF(pl_presbytry, ''), '') <> ''
            ) presbyteries
        ), '[]'::jsonb)
    )
) || maps.get_land_report_breakdowns(payload)
   || jsonb_build_object('expiringLeases', maps.get_land_report_expiring_leases(payload));
$$;
