data "google_project" "project" {}

locals {
  cloud_build_sa = "${data.google_project.project.number}-compute@developer.gserviceaccount.com"
}

resource "google_project_iam_member" "cloudbuild_run_admin" {
  project = data.google_project.project.project_id
  role    = "roles/run.admin"
  member  = "serviceAccount:${local.cloud_build_sa}"
  depends_on = [google_project_service.apis]
}

resource "google_project_iam_member" "cloudbuild_sa_user" {
  project = data.google_project.project.project_id
  role    = "roles/iam.serviceAccountUser"
  member  = "serviceAccount:${local.cloud_build_sa}"
  depends_on = [google_project_service.apis]
}

resource "google_project_iam_member" "cloudbuild_artifact_registry" {
  project = data.google_project.project.project_id
  role    = "roles/artifactregistry.writer"
  member  = "serviceAccount:${local.cloud_build_sa}"
  depends_on = [google_project_service.apis]
}

resource "google_secret_manager_secret_iam_member" "secret_access" {
  secret_id = google_secret_manager_secret.db_password.id
  role      = "roles/secretmanager.secretAccessor"
  member    = "serviceAccount:${data.google_project.project.number}-compute@developer.gserviceaccount.com"
}