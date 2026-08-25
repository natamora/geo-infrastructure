resource "google_service_account" "terraform_trigger_sa" {
  account_id = "terraform-deployer-sa"
  display_name = "Cloud Build Trigger SA for Terraform"
}

resource "google_project_iam_member" "tf_sa_editor" {
  project = var.project_id
  role    = "roles/editor"
  member  = "serviceAccount:${google_service_account.terraform_trigger_sa.email}"
}

resource "google_project_iam_member" "tf_sa_iam_admin" {
  project = var.project_id
  role    = "roles/resourcemanager.projectIamAdmin"
  member  = "serviceAccount:${google_service_account.terraform_trigger_sa.email}"
}

resource "google_secret_manager_secret_iam_member" "tf_sa_secret_access" {
  secret_id = google_secret_manager_secret.db_password.id
  role      = "roles/secretmanager.secretAccessor"
  member    = "serviceAccount:${google_service_account.terraform_trigger_sa.email}"
}