FROM payara/micro:5.2022.1
COPY target/Teste-1.0.war $DEPLOY_DIR
CMD ["--deploymentDir", "/opt/payara/deployments","--port","5000","--contextroot","ROOT", "--clustermode", "kubernetes"]
EXPOSE 5000
