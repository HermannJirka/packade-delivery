    pipeline {
        agent none

        stages{
            stage("Build & test"){
                matrix{
                    agent {
                        dockerfile{
                            label "docker"
                            additionalBuildArgs """
                                --build-arg JAVA_VERSION=$JAVA\
                                --build-arg MAVEN_VERSION=$MAVEN \
                                --build-arg USER_ID=\$(id -u) \
                                -t mymaven:${MAVEN}-jdk-${JAVA}
                            """.stripIndent().trim()

                            args "-v /tmp/maven:/home/jenkins/.m2"
                        }
                    }
                     axes{
                         axis{
                            name "JAVA"
                            values "11.0.7-amzn", "15.ea.26-open"
                         }
                         axis{
                            name "MAVEN"
                            values "3.6.3"
                         }
                     }
                    stages{
                        stage("Build"){
                            steps {
                                sh "mvn -DskipTests clean package"
                            }
                        }
                        stage("Test"){
                            steps{
                                sh "mvn test -P coverage"
                            }
                        }
                    }
                }
            }
        }
    }