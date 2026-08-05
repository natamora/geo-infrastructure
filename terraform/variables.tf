variable "project_id" {
  description = "ID of the GCP project"
  type        = string
  default     = "geo-infrastructure"
}

variable "region" {
  description = "Default region for resources"
  type        = string
  default     = "europe-central2"
}

variable "db_password" {
  description = "Database password"
  type        = string
  sensitive   = true
}

variable "artifact_repo_name" {
  description = "Artifact Registry repository ID"
  type        = string
  default     = "geo-repo"
}

variable "cloud_run_name" {
  description = "Name for cloud run app"
  type        = string
  default     = "gis-app"
}
