# Github-User-Activity

# Read Me First

* This application fetches public user activity from GitHub's public API.
* This application was created using Java and Spring Boot

# Getting Started

### Features
* Fetches user activity from GitHub's public API
* Implements a REST endpoint to retrieve GitHub events for a username
* eventType, repoName, and actor are query parameters for filtering options
### Query Example: 
1. http://localhost:8080/github/activity/{username}?eventType={your-event-type}&repoName={your-repo-name}&actor={your-actor.login}
