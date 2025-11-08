# Question-DynamoDB, Lambda and EventBridge
Design and implement a real-time data processing pipeline for an e-commerce application that handles order events with the following requirements:
1. DynamoDB Streams: Configure a DynamoDB table Orders that streams all CRUD operations (INSERT, MODIFY, REMOVE)
2. EventBridge Pipes: Create an EventBridge Pipe that:
- Source: DynamoDB stream from the Orders table
- Filtering: Only process events where:
  - Order status is "pending" OR "shipped"
  - Order amount is greater than $100
  - Exclude test orders (customerEmail does not contain "test.com")
- Target: Lambda function for processing filtered orders
3. Advanced Filtering: Implement a second pipe that:
- Filters for high-value orders (amount > $1000)
- Routes to a different Lambda function for premium customer service
- Includes only MODIFY events where status changes from "pending" to "shipped"
4. Error Handling: Configure dead-letter queues for failed events and implement retry logic for transient failures

### Technical Requirements:
Use DynamoDB Streams with NEW_AND_OLD_IMAGES<br />
Implement EventBridge Pipes with exact field matching and pattern matching<br />
Create appropriate IAM roles for cross-service permissions<br />
Handle batch processing and partial failures<br />

### Evaluation Focus:
Proper configuration of DynamoDB streams<br />
Effective use of EventBridge Pipes filtering capabilities<br />
Efficient event pattern design<br />
Error handling and reliability patterns<br />
Cost optimization through appropriate filtering<br />

### Deliverables:
CloudFormation/Terraform templates<br />
Filter patterns and pipe configurations<br />
Lambda function code for processing<br />
Architecture diagram showing the complete pipeline<br />


### Logs
<pre>
  Received Normal Order Event: [{
    "eventID": "fa58d82a0f8dabc763bd3af48953885c",
    "eventName": "INSERT",
    "eventVersion": "1.1",
    "eventSource": "aws:dynamodb",
    "awsRegion": "us-east-1",
    "dynamodb": {
        "ApproximateCreationDateTime": 1761915500,
        "Keys": {
            "orderId": {
                "S": "order123"
            }
        },
        "NewImage": {
            "amount": {
                "N": "500"
            },
            "customerType": {
                "S": "normal"
            },
            "orderId": {
                "S": "order123"
            }
        },
        "SequenceNumber": "16000004404151851219162",
        "SizeBytes": 56,
        "StreamViewType": "NEW_AND_OLD_IMAGES"
    },
    "eventSourceARN": "arn:aws:dynamodb:us-east-1:433980226768:table/Orders/stream/2025-10-31T12:53:41.284"
}]
</pre>
