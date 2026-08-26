output "cloud_run_url" {
  description = "Public URL of the Cloud Run service"
  value       = google_cloud_run_v2_service.app.uri
}

output "artifact_registry_repository" {
  description = "URI of the Docker repository"
  value       = google_artifact_registry_repository.repo.id
}

output "db_connection_name" {
  description = "Db connection name"
  value       = google_sql_database_instance.postgres_instance.connection_name
}