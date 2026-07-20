# :globe_with_meridians: Geo-Infrastructure API (WIP)
A GeoSpatial API built to serve as a technical sandbox for mastering Spring Boot, modern Java and GIS technologies.

## Project Vision
This project is a backend-focused engineering sandbox designed to master the spectrum of backend development and explore the integration of geospatial data processing with modern **Spring Boot 3.x** and **Java 21**. 

The primary objective is to build REST API that models infrastructure networks, maintaining topological relationships between Nodes (Point), Cables (LineString) and Zones (Polygon).

The system architecture utilizes PostGIS to enable advanced geometric opeations. By simulating infrastructure dependencies, the project allows for testing spatial querying, such as point-in-polygon containment.
The app will implement custom business logic to evaluate infrastructure risk and trigger maintenance task lifecycles. Future deployment is planned on Google Cloud Run.

Technically, the proejct is testbed for:
* Modern Java: Leveraging Java 21 features
* Spring Ecosystem: Mastering dependency injection, REST controller design, validation and comprehensive testing strategies
* Database Management & ORM - Bridging Hibernate/JPA integration with PostgreSQL/PostGIS, managing entity relationships and utilizing spatial indexing
* DevOps Foundation - Ensuring infrastructure reproducibility and automated testing pipelines

The goal is to deliver backend solution that utilizes Spring-based architecture with added analytical capability of spatial data processing.

## :hammer: Technology & Tooling
* **Core Backend**: Java 21, Spring Boot 3.x
* **Persistence & Spatial**: PostgreSQL with PostGIS, Hibernate/JPA, JTS
* **Contenerization & DevOps**: Docker, Docker Compose, Google Cloud Run (target)
* **Testing**: JUnit 5, Mockito, AssertJ, Testcontainers (target for ephemeral database testing)
* **API & Data**: RESTful architecture, GeoJSON for spatial data serialization
* **Frontend**: TypeScript, React, TanStack Query, Zustand, Leaflet

## :white_check_mark: Implementation Status
* Basic Frontend: Adding dynamic layers, Shape geometry creation, DetailsPanel, LayerTree
* Basic CRUD endpoints for objects
*  Project skeleton with JTS and PostGIS integration
* Basic GET endpoints for geometries with GeoJSON mapping with BBOX filtering
* Basic endpoint with spatial filtering (ST_Intersects)
* Simple global exception handling
* Basic multi-layered test coverage example (Unit, Service, Controller and Integration tests)

### :dart: Most recent pending goals: 
* [ ] Improve frontend (WIP)
* [ ] Full Node and Cable implementation (WIP)
* [ ] Data validation (WIP)
* [ ] Risk Assessment Engine & maintenance task generation logic
* [ ] Advanced API filtering
* [ ] Integrating Swagger (WIP)
* [ ] Spring Security
* [ ] Cloud-native migration and deployment with Google Cloud Run (WIP)

Application goals gonna be updated by the evolving of the application.


