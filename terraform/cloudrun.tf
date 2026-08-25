resource "google_cloud_run_v2_service" "app" {
  name     = var.cloud_run_name
  location = var.region
  ingress  = "INGRESS_TRAFFIC_ALL"

  template {
    scaling {
      max_instance_count = 3
      min_instance_count = 1
    }
    containers {
      image = "us-docker.pkg.dev/cloudrun/container/hello"

      ports {
        container_port = 8080
      }

      env {
        name  = "SPRING_PROFILES_ACTIVE"
        value = "cloud"
      }
      env {
        name = "DB_PASSWORD"
        value_source {
          secret_key_ref {
            secret  = google_secret_manager_secret.db_password.secret_id
            version = "latest"
          }
        }
      }
      env {
        name  = "DB_HOST"
        value = google_sql_database_instance.postgres_instance.public_ip_address
      }
    }
  }

  lifecycle {
    ignore_changes = [
      template,
    ]
  }

  depends_on = [
    google_secret_manager_secret_version.db_password_version,
    google_secret_manager_secret_iam_member.tf_sa_secret_access,
    google_project_service.apis
  ]
}

resource "google_cloud_run_v2_service_iam_member" "public_access" {
  project  = google_cloud_run_v2_service.app.project
  location = google_cloud_run_v2_service.app.location
  name     = google_cloud_run_v2_service.app.name
  role     = "roles/run.invoker"
  member   = "allUsers"
}

output "cloud_run_url" {
  value = google_cloud_run_v2_service.app.uri
}
