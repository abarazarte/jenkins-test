# Operar con las migraciones

## Parametro env

  Algunos script soportan el envio del ambiente de ejecución mediante el parametro --env, si no se envia
  por defecto será development

## Ver estado de migraciones

  > ./migrate-status.sh [--env]

  Ejemplo 1: Mostrará el estado de las migraciones en el ambiente por defecto "development"

    > ./migrate-status.sh --env=development

  Ejemplo 2: Mostrará el estado de las migraciones en el ambiente definido en el parametro "qa"

      > ./migrate-status.sh --env=qa

## Ejecucion de migraciones

  > ./migrate-up.sh [--env]

  Ejemplo 1: Ejecutará las migraciones en el ambiente por defecto "development"

    > ./migrate-up.sh

  Ejemplo 2: Ejecutará las migraciones en el ambiente definido en el parametro "qa"

  > ./migrate-up.sh --env=qa

## Rollback de migraciones

  > ./migrate-down.sh [--env] [steps]

  Ejemplo 1: Realizará rollback de la última migración en el ambiente por defecto "development"

    > ./migrate-down.sh

  Ejemplo 2: Realizará rollback de la última migración en el ambiente definido en el parametro "qa"

      > ./migrate-down.sh --env=qa

  Ejemplo 3: Realizará rollback de las últimas 2 migraciones en el ambiente por defecto "development"

      > ./migrate-down.sh 2

  Ejemplo 3: Realizará rollback de las últimas 2 migraciones en el ambiente definido en el parametro "qa"

        > ./migrate-down.sh --env=qa 2

## Crear nueva migración

  > ./migrate-new.sh NOMBRE_DE_LA_MIGRACION

  Ejemplo 1: Creara una migracion con e nombre "create_table_users"

    > ./migrate-new.sh create_table_users

# Ejecución de test

  ## Ejecutar los test en el ambiente por defecto "development"

    > ./test.sh

  ## Ejecutar los test en el ambiente definido en el paramtro "qa"

      > ./test.sh qa
