resource "google_sql_database_instance" "postgres_instance" {
  name             = "gis-db-instance"
  database_version = "POSTGRES_16"
  region           = var.region

  settings {
    tier = "db-custom-1-3840"
    ip_configuration {
      ipv4_enabled = true
      authorized_networks {
        name  = "my-pc"
        value = "0.0.0.0/0"
      }
    }
  }
  deletion_protection = false
  depends_on          = [google_project_service.apis]
}

resource "google_sql_database" "database" {
  name     = "gis_db"
  instance = google_sql_database_instance.postgres_instance.name
}

resource "google_sql_user" "user" {
  name     = "postgres"
  instance = google_sql_database_instance.postgres_instance.name
  password = var.db_password
}