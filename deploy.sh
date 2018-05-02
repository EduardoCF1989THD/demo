PROJECT_ID="helical-sled-202720"
IMAGE_NAME="application-with-secrets"
CLUSTER_NAME="demo-cluster"
REGION_OR_ZONE="us-central1-c"
TAG_NAME=$1


###   Point your terminal at the desired project, region, and cluster before deploying the image
gcloud config set project $PROJECT_ID
gcloud config set compute/zone $REGION_OR_ZONE
gcloud config set container/use_v1_api_client false   ##required step before pulling credentials in next step
gcloud beta container clusters get-credentials $CLUSTER_NAME --region $REGION_OR_ZONE  ##if $REGION_OR_ZONE is a ZONE, the flag should be changed


if [ -z "$TAG_NAME" ]
then
  echo "Must provide tag name as parameter";
  exit 1;
fi;

echo "Using project $PROJECT_ID"
echo "Using image $IMAGE_NAME : $TAG_NAME"

gcloud config set project $PROJECT_ID
docker build -t gcr.io/$PROJECT_ID/$IMAGE_NAME:$TAG_NAME .

echo "Dockerfile image successfully created"
gcloud docker -- push gcr.io/$PROJECT_ID/$IMAGE_NAME:$TAG_NAME

echo "Creating YAML and deploying..."
sed "s|{{app_name}}|$IMAGE_NAME|" template.yml | sed "s|{{image_name}}|gcr.io/$PROJECT_ID/$IMAGE_NAME:$TAG_NAME|" > deployment.yml
echo '

## this file was created on '"$(date)"'.  Image:Tag = '"$IMAGE_NAME:$TAG_NAME" >> deployment.yml

kubectl apply -f ./deployment.yml

echo " = = = = = >  Done. You are now waiting for $IMAGE_NAME:$TAG_NAME replicate.";


###  To externalize configuration using a ConfigMap, point to the appropriate cluster and push the YAML.
###    This will affect ALL deployments in the cluster with colliding filename && key:value pairs