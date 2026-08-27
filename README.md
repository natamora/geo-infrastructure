# :globe_with_meridians: Geo-Infrastructure App (WIP)
[![Live Demo](https://img.shields.io/badge/Live%20Demo-Try%20Now-green?style=flat-square)](https://gis-app-80843192607.europe-central2.run.app)

> [!WARNING]
> **Note:** The webpage may take a few minutes to load due to cold start.

A Full-Stack GeoSpatial application built to serve as a technical sandbox for mastering modern Java, Spring Boot and GIS technologies.

---

## Project Vision
This project is an engineering sandbox designed to explore the integration of geospatial data processing with a modern **Spring Boot 3.x** and **Java 21** backend, paired with an interactive **React** frontend. 

The application models and visualizes infrastructure networks. It provides an interactive map where users can manage Nodes (Point), Cables (LineString) and Zones (Polygon).

The core functionality allows users to:
* Toggle and browse different infrastructure layers
* Click on specific objects on the map to view their detailed properties
* Draw and create new infrastructure shapes directly via the UI

Behind the scenes, the system utilizes **PostgreSQL + PostGIS** to maintain topological relationships and enable advanced geometric operations (e.g., spatial filtering or point-in-polygon containment). In the future, the app will implement custom business logic to evaluate infrastructure risks and trigger maintenance task lifecycles. It will also use **Google Cloud Functions** to prepare and send a weekly report about the state of the infrastructure.

Technically, the project is a testbed for:
* **Modern Java:** Leveraging Java 21 features
* **Spring Ecosystem:** Mastering dependency injection, REST controller design, data validation, and testing strategies
* **Database Management & ORM:** Bridging Hibernate/JPA integration with PostGIS, managing entity relationships, and utilizing spatial indexing
* **Frontend Integration:** A dedicated interactive client built with TypeScript, React, TanStack Query, Zustand, and Leaflet.
* **Cloud & DevOps:** Provisioning GCP infrastructure via **Terraform**, managing automated GitOps pipelines in **Cloud Build**, and serverless hosting on **Cloud Run**.

## :hammer: Technology & Tooling
* **Core Backend**: Java 21, Spring Boot 3.x
* **Persistence & Spatial**: PostgreSQL with PostGIS, Hibernate/JPA, JTS
* **Cloud & DevOps**: Google Cloud Run, Google Cloud Build, Artifact Registry, Terraform, Docker
* **Testing**: JUnit 5, Mockito, AssertJ, Testcontainers (target for ephemeral database testing)
* **API & Data**: RESTful architecture, GeoJSON for spatial data serialization
* **Frontend**: TypeScript, React, TanStack Query, Zustand, Leaflet

---

## :building_construction: Architecture & CI/CD
This project is deployed on **Google Cloud Platform (GCP)** using **Terraform** for Infrastructure as Code (IaC) and **Google Cloud Build** for automated CI/CD pipelines.

### Managed Resources
* **Cloud Run (`cloudrun.tf`)** - Serverless  environment running app container
* **Cloud SQL (`db.tf`)** – Managed PostgreSQL database
* **Secret Manager (`secret.tf`)** – Storage for sensitive data (database password)
* **Artifact Registry (`artifact-registry.tf`)** – Repository for Docker images
* **IAM (`iam_*.tf`)** – Role bindings for Service Accounts
  
### Overview

The CI/CD workflow is fully automated via **Google Cloud Build** and divided into two separate GitOps pipelines to avoid unnecessary redeployments:

* **Infrastructure Pipeline:** Triggered only when `.tf` files change.
* **Application Pipeline:** Triggered by updates to the source code (ignoring Terraform configurations). It builds the Docker image, pushes it to Artifact Registry, and deploys the new revision to Cloud Run
  
```text
[ Git Push ] 
        │
        ▼
   [ Cloud Build ] 
        ├── (1) Infrastructure Pipeline ──► Updates GCP Infrastructure
        │                                  ├── Cloud SQL (PostgreSQL Database)
        │                                  ├── Secret Manager (Passwords)
        │                                  ├── Artifact Registry (Docker Repositories)
        │                                  └── Cloud Run v2 (Serverless Application)
        │
        └── (2) Deployment Pipeline ──► Build Docker Image, Pushes it to Artifact Registry & Deploys to Cloud Run
                                           │
                                           ▼
                                    [ Cloud Run Service ] ──► [ Cloud SQL ]
```

### :lock: IAM
To ensure a secure separation of concerns, the project utilizes dedicated Service Accounts (SA) with minimal required permissions:
1. **Terraform Deployer SA (`terraform-deployer-sa`)**
   * Provisions and updates GCP infrastructure.
2. **Cloud Build Trigger SA (`cloudbuild-trigger-sa`)**
   * Handles the CI/CD application pipeline. Can push images to Artifact Registry, update Cloud Run services, and manage storage/logs.
3. **App Runtime SA (`app-runtime-sa`)**
   * Attached directly to the Cloud Run service to execute the application code. Currently allows the app to securely access database password from secret manager

---

## :white_check_mark: Implementation Status
* Fully containerized and automated deployment pipeline (Terraform + Cloud Build + Cloud Run) with strict IAM Service Account segregation
* Basic interactive UI with dynamic layers, Shape geometry creation, DetailsPanel, and LayerTree
* Core CRUD endpoints for infrastructure objects with simple exception handling
* Project skeleton with JTS and PostGIS integration
* GET endpoints for geometries with GeoJSON mapping and BBOX filtering, and basic spatial filtering (ST_Intersects).
* Basic multi-layered test coverage example (Unit, Service, Controller and Integration tests)
* API object filtering by types, date range etc.
* Basic integration with Swagger

### :dart: Most recent pending goals: 
* [ ] Secure database connection by migrating Cloud SQL from Public IP to Private IP (VPC) / Cloud SQL Auth Proxy
* [ ] Implement frontend UI for date-based shape filtering (WIP)
* [ ] Full Data validation (WIP)
* [ ] Risk Assessment Engine & maintenance task generation logic
* [ ] Automated weekly update email sending 
* [ ] Spring Security

Application goals will be updated as the project evolves.
