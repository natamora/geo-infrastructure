# Artifact Registry: Docker repository
resource "google_artifact_registry_repository" "repo" {
  provider = google
  location = var.region
  repository_id = var.artifact_repo_name
  description   = "Repository for Docker images"
  format        = "DOCKER"
  depends_on    = [google_project_service.apis]
}