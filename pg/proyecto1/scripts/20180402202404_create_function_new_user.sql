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

-- // add new_user procedure
-- Migration SQL that makes the change goes here.

CREATE OR REPLACE FUNCTION proyecto1.add_user(p_name VARCHAR(100), p_email VARCHAR(100)) RETURNS integer AS $$
DECLARE
   r_id integer;
BEGIN
    INSERT INTO proyecto1.Users(name, email) VALUES (p_name, p_email)
    RETURNING id INTO r_id;
    RETURN r_id;
END;
$$ LANGUAGE plpgsql;

-- //@UNDO
-- SQL to undo the change goes here.

DROP FUNCTION IF EXISTS proyecto1.add_user(VARCHAR(100), VARCHAR(100));


