import json
import boto3
from botocore.exceptions import ClientError

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('UserSubmissions')

def lambda_handler(event, context):
    try:
        email_filter = None
        if 'queryStringParameters' in event and event['queryStringParameters']:
            email_filter = event['queryStringParameters'].get('email')
        
        if email_filter:
            # Scan with filter
            response = table.scan(
                FilterExpression='email = :e',
                ExpressionAttributeValues={':e': email_filter}
            )
        else:
            # Scan all records
            response = table.scan()
        
        items = response.get('Items', [])
        
        return {
            "statusCode": 200,
            "headers": {"Access-Control-Allow-Origin": "*"},
            "body": json.dumps(items)
        }
        
    except ClientError as e:
        return {
            "statusCode": 500,
            "headers": {"Access-Control-Allow-Origin": "*"},
            "body": json.dumps({"error": str(e)})
        }
