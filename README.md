# shared-library

In Jenkins, a shared library is a way to centrally manage and share reusable code across multiple Jenkins pipelines. This is particularly useful in large projects or organizations where common code snippets, functions, or pipeline steps need to be reused by different Jenkinsfiles.

### Key Concepts

1. **Central Repository**: Shared libraries are stored in a version control system like Git. They have a predefined structure and can be accessed by multiple Jenkins pipelines.

2. **Library Structure**:
   - **vars/**: Contains global variables and functions. Each file (without the `.groovy` extension) represents a global variable.
   - **src/**: Contains Groovy classes that can be used in the pipeline scripts.
   - **resources/**: Stores non-Groovy files that might be used by the library (e.g., scripts, configuration files).
   - **examples/**: Optional directory to provide examples of how to use the library functions.

3. **Loading the Library**: The library can be loaded into a Jenkins pipeline using the `@Library` annotation or by specifying it in the Jenkins configuration.

### Usage

1. **Defining a Shared Library**:
   - Create a new repository in your version control system.
   - Structure the repository as mentioned above.
   - Write reusable functions, classes, and scripts.

2. **Configuring Jenkins**:
   - Go to **Manage Jenkins** -> **Configure System**.
   - Scroll down to **Global Pipeline Libraries**.
   - Add a new library with the required details like the library name, default version, and the repository URL.

3. **Using the Library in Pipelines**:
   - In your Jenkinsfile, load the library using the `@Library` annotation:
     ```groovy
     @Library('my-shared-library') _
     ```
   - Use the shared functions or variables in your pipeline:
     ```groovy
     pipeline {
         agent any
         stages {
             stage('Example') {
                 steps {
                     script {
                         mySharedFunction()
                     }
                 }
             }
         }
     }
     ```

### Benefits

- **Code Reusability**: Reduces code duplication by allowing common functions to be reused across multiple pipelines.
- **Maintenance**: Easier to update and maintain shared logic in a single place.
- **Consistency**: Ensures that all pipelines follow the same standards and practices.




### Example

Suppose you have a common function to send notifications. You can define it in your shared library like this:

**vars/notify.groovy**:
```groovy
def call(String message) {
    echo "Sending notification: ${message}"
}
```

**Jenkinsfile**:
```groovy
@Library('my-shared-library') _
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    notify('Build started')
                }
            }
        }
    }
}
```

In this example, the `notify` function from the shared library is used in the pipeline to send a notification.
