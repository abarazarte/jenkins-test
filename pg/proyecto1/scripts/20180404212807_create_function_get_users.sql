--
--    Copyright 2010-2016 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

-- // create_function_get_users
-- Migration SQL that makes the change goes here.

CREATE OR REPLACE FUNCTION proyecto1.get_users() RETURNS
TABLE(id INT, name VARCHAR, email VARCHAR) AS $BODY$
BEGIN
    RETURN QUERY
    SELECT u.id, u.name, u.email FROM proyecto1.Users as u;
END;
$BODY$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION proyecto1.get_user_by_name(p_name VARCHAR(100)) RETURNS
TABLE(id INT, name VARCHAR, email VARCHAR) AS $BODY$
BEGIN
    RETURN QUERY
    SELECT u.id, u.name, u.email FROM proyecto1.Users as u where u.name = p_name;
END;
$BODY$ LANGUAGE plpgsql;

-- //@UNDO
-- SQL to undo the change goes here.

DROP FUNCTION IF EXISTS proyecto1.get_users();

DROP FUNCTION IF EXISTS proyecto1.get_user_by_name(VARCHAR);


