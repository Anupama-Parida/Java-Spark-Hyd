import json
import boto3
import datetime
import uuid
from botocore.exceptions import ClientError

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('UserSubmissions')

def lambda_handler(event, context):
    try:
        # Parse JSON body
        body = json.loads(event['body'])
        name = body.get('name')
        email = body.get('email')
        message = body.get('message')
        
        if not name or not email or not message:
            return {
                "statusCode": 400,
                "headers": {"Access-Control-Allow-Origin": "*"},
                "body": json.dumps({"error": "name, email, and message are required"})
            }
        
        submission_id = str(uuid.uuid4())
        submission_date = datetime.datetime.utcnow().isoformat()
        status = "submitted"
        
        # Insert into DynamoDB
        table.put_item(
            Item={
                "submissionId": submission_id,
                "name": name,
                "email": email,
                "message": message,
                "submissionDate": submission_date,
                "status": status
            }
        )
        
        return {
            "statusCode": 200,
            "headers": {"Access-Control-Allow-Origin": "*"},
            "body": json.dumps({"message": "Submission successful", "submissionId": submission_id})
        }
        
    except ClientError as e:
        return {
            "statusCode": 500,
            "headers": {"Access-Control-Allow-Origin": "*"},
            "body": json.dumps({"error": str(e)})
        }
