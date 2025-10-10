pipeline {
    agent any

    parameters {
        string(name: 'TEST_BRANCH_NAME', defaultValue: 'master', description: 'Branch to build')
    }

    environment {
        base_git_url = "https://gitlab.com/epickonfetka/cicd-threadqa.git"
    }

    stages {
        stage('Checkout Branch') {
            steps {
                script {
                    def task_branch = params.TEST_BRANCH_NAME
                    def branch_cutted = task_branch.contains("origin") ? task_branch.split('/')[1] : task_branch.trim()
                    currentBuild.displayName = branch_cutted
                    if (!branch_cutted.contains("master")) {
                        try {
                            cleanWs()
                            checkout([
                                    $class: 'GitSCM',
                                    branches: [[name: branch_cutted]],
                                    userRemoteConfigs: [[url: env.base_git_url]]
                            ])
                        } catch (err) {
                            echo "Failed get branch ${branch_cutted}"
                            throw err
                        }
                    } else {
                        echo "Current branch is master"
                    }
                }
            }
        }

        stage('Parallel Test Execution') {
            parallel {
                stage('API Tests') {
                    steps {
                        sh './gradlew clean test -PtestTags=api'
                    }
                }
                stage('UI Tests') {
                    steps {
                        sh './gradlew clean test -PtestTags=ui'
                    }
                }
            }
        }

        stage('Build Snapshot JAR') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                        includeProperties: true,
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'build/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}