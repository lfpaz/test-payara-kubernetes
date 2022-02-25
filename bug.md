# Description #

 CDI/JSF Session Beans (@Named and @SessionScoped) is not retrieved correctly by payara micro with cluster in a kubernetes using a loadbalancer (nginx or traefik)  using payara/micro:5.2022.1 docker image.

## Expected Outcome ##

The (@Named and @SessionScoped) stored data should be correctly recovered during the session time.

## Current Outcome ##

If using JSF/CDI (@Named and @SessionScoped) stored data don't be showed across responses.

If the data is stored in the session directly with Servlet (request.getSession(true).setAttribute(key, value)) the data is recovered correctly.

If the app is deployed with only one POD the works correctly.

## Steps to reproduce ## 

Deploy the aplication https://github.com/lfpaz/test-payara-kubernetes on a k8s cluster with at least 2 POD's with a loadbalancer (nginx or traefik), using the DockerFile.

Open the app and in the "Session With CDI/JSF Test - Kubernetes"  Put some data in key and value and click "add". If the POD is changed by loadbalancer the data is not showed correctly.

Click on the link and view the same app with "Session With Servlet Test - Kubernetes". Put some data in key and value and click "add". Even if the POD is changed by the loadbalancer the data is showed correctly.

## Environment ##

- Docker image payara/micro:5.2022.1
- Rancher v1.21.9 with kubernetes v1.21.9 
- Loadbalancers testex with nginx and traefik 
- Deployed with self hosted gitlab
- On self hosted servers

