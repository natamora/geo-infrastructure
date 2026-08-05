resource "google_service_account" "cloudbuild_trigger_sa" {
  account_id   = "cloudbuild-trigger-sa"
  display_name = "Cloud Build Trigger Service Account"
}

resource "google_project_iam_member" "trigger_sa_builds_editor" {
  project = var.project_id
  role    = "roles/cloudbuild.builds.editor"
  member  = "serviceAccount:${google_service_account.cloudbuild_trigger_sa.email}"
}

resource "google_project_iam_member" "trigger_sa_storage_admin" {
  project = var.project_id
  role    = "roles/storage.admin"
  member  = "serviceAccount:${google_service_account.cloudbuild_trigger_sa.email}"
}

resource "google_project_iam_member" "trigger_sa_ar_writer" {
  project = var.project_id
  role    = "roles/artifactregistry.writer"
  member  = "serviceAccount:${google_service_account.cloudbuild_trigger_sa.email}"
}

resource "google_project_iam_member" "trigger_sa_run_admin" {
  project = var.project_id
  role    = "roles/run.admin"
  member  = "serviceAccount:${google_service_account.cloudbuild_trigger_sa.email}"
}

resource "google_project_iam_member" "trigger_sa_user" {
  project = var.project_id
  role    = "roles/iam.serviceAccountUser"
  member  = "serviceAccount:${google_service_account.cloudbuild_trigger_sa.email}"
}