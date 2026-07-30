CREATE OR REPLACE FUNCTION maps.get_land_polygons(payload jsonb DEFAULT '{}'::jsonb)
RETURNS jsonb
LANGUAGE sql
AS $$
WITH filtered AS (
    SELECT
        pl.pl_id,
        pl.pl_glpin,
        pl.pl_presbytry,
        pl.pl_district,
        pl.pl_congregation,
        pl.pl_congregation_address,
        pl.pl_locality,
        pl.pl_land_size_acre,
        pl.pl_land_size_hectare,
        pl.pl_development_status,
        pl.pl_occupation_of_land,
        pl.pl_type_of_ownership,
        pl.pl_has_any_documents,
        pl.pl_is_land_registered,
        pl.pl_status_of_registration,
        pl.pl_is_dispute_on_land,
        pl.pl_type_of_boundary_on_land,
        pl.pl_type_of_building_on_land,
        pl.date_of_expiration,
        pl.created_date,
        pl.modified_date,
        pl.district_id,
        pl.congregation_id,
        ST_NPoints(pl.geom) AS vertex_count,
        ST_AsText(pl.geom) AS wkt_polygon
    FROM maps.preby_lands pl
    WHERE pl.geom IS NOT NULL
      AND (COALESCE(payload->>'presbytery', '') = '' OR pl.pl_presbytry = payload->>'presbytery')
      AND (COALESCE(payload->>'districtId', '') = '' OR pl.district_id = NULLIF(payload->>'districtId', '')::bigint)
      AND (COALESCE(payload->>'district', '') = '' OR pl.pl_district = payload->>'district')
      AND (COALESCE(payload->>'congregationId', '') = '' OR pl.congregation_id = NULLIF(payload->>'congregationId', '')::bigint)
      AND (COALESCE(payload->>'congregation', '') = '' OR pl.pl_congregation = payload->>'congregation')
      AND (
            COALESCE(payload->>'search', '') = ''
            OR concat_ws(
                ' ',
                COALESCE(pl.pl_glpin, ''),
                COALESCE(pl.pl_presbytry, ''),
                COALESCE(pl.pl_district, ''),
                COALESCE(pl.pl_congregation, ''),
                COALESCE(pl.pl_locality, '')
            ) ILIKE '%' || (payload->>'search') || '%'
          )
)
SELECT jsonb_build_object(
    'success', true,
    'summary', jsonb_build_object(
        'totalCount', COUNT(*),
        'totalAcreage', COALESCE(ROUND(SUM(COALESCE(pl_land_size_acre, 0)), 3), 0),
        'totalHectares', COALESCE(ROUND(SUM(COALESCE(pl_land_size_hectare, 0)), 3), 0),
        'totalVertices', COALESCE(SUM(COALESCE(vertex_count, 0)), 0),
        'registeredCount', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_is_land_registered, '')) = 'YES'),
        'documentCount', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_has_any_documents, '')) = 'YES'),
        'disputeCount', COUNT(*) FILTER (WHERE UPPER(COALESCE(pl_is_dispute_on_land, '')) = 'YES')
    ),
    'filterOptions', jsonb_build_object(
        'presbyteries', COALESCE((
            SELECT jsonb_agg(presbytery_name ORDER BY presbytery_name)
            FROM (
                SELECT DISTINCT pl_presbytry AS presbytery_name
                FROM maps.preby_lands
                WHERE COALESCE(NULLIF(pl_presbytry, ''), '') <> ''
            ) presbyteries
        ), '[]'::jsonb)
    ),
    'data', COALESCE(jsonb_agg(jsonb_build_object(
        'plId', pl_id,
        'glpin', pl_glpin,
        'presbytery', pl_presbytry,
        'district', pl_district,
        'congregation', pl_congregation,
        'congregationAddress', pl_congregation_address,
        'locality', pl_locality,
        'landSizeAcre', pl_land_size_acre,
        'landSizeHectare', pl_land_size_hectare,
        'developmentStatus', pl_development_status,
        'occupationOfLand', pl_occupation_of_land,
        'ownershipType', pl_type_of_ownership,
        'hasDocuments', pl_has_any_documents,
        'isLandRegistered', pl_is_land_registered,
        'registrationStatus', pl_status_of_registration,
        'hasDispute', pl_is_dispute_on_land,
        'boundaryType', pl_type_of_boundary_on_land,
        'buildingType', pl_type_of_building_on_land,
        'expirationDate', date_of_expiration,
        'createdDate', created_date,
        'modifiedDate', modified_date,
        'districtId', district_id,
        'congregationId', congregation_id,
        'vertexCount', vertex_count,
        'wktPolygon', wkt_polygon
    ) ORDER BY pl_presbytry, pl_district, pl_congregation, pl_id), '[]'::jsonb)
)
FROM filtered;
$$;
