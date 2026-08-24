terraform {
  backend "gcs" {
    bucket = "geo-infrastructure-terraform-state"
    prefix = "terraform/state"
  }
}