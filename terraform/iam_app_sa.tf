resource "google_service_account" "app_runtime_sa" {
  account_id   = "app-runtime-sa"
  display_name = "Service Account for Cloud Run App"
}

resource "google_secret_manager_secret_iam_member" "app_secret_access" {
  secret_id = google_secret_manager_secret.db_password.id
  role      = "roles/secretmanager.secretAccessor"
  member    = "serviceAccount:${google_service_account.app_runtime_sa.email}"

  lifecycle {
    create_before_destroy = true
  }
}