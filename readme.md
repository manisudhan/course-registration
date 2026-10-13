Build, Test, and Package Commands
-open windows powershell and navigate to the directory, then type 'mvn clean test'.
-mvn package

Deployment Commands
Start Minikube: minikube start --driver=docker
Apply Kubernetes Manifests: kubectl apply -f k8s/deployment.yaml
Verify Deployment & Pod Status: kubectl get pods

Git Evidence:
-Feature Branch Name: feature/course-registration-refactor
commit history:
PS C:\Users\SNUC\Downloads\Student-Starter\Student-Starter> git log --oneline
-e4bfbe1 (HEAD -> feature/course-registration-refactor) Complete multi-module Maven refactoring and verify reactor build with parent POM
-27887d2 Refactor project into multi-module Maven structure with parent POM
-cef2bff (main) Initial student assessment starter

Maven Configuration and Build Evidence:
 -Build/Test Results: The mvn clean test execution successfully compiles both modules, executes all unit tests without failure, and generates standard Surefire XML test reports.