# Factory Candy Management System

The Factory Candy Management System is a comprehensive software application designed to streamline and automate the operations of a candy factory. It offers a range of GUI applications tailored to meet the specific needs of the factory, customers, and raw material distributors. The system incorporates various technologies, including RMI, REST, Sockets, Secure Sockets, RabbitMQ, and Redis, to ensure efficient communication and data management across the different components.

## Key Features

- User Management:
  - Factory operators can manage customer accounts, including user registration approval, account deletion, and blocking/unblocking functionality.
- Product Management:
  - CRUD operations enable the factory to manage its product catalog, including adding, updating, and removing candy products.
- Order Processing:
  - Customers can place orders through the customer application, selecting from a wide range of available candy products. Orders are then sent to the factory via the RESTful endpoint, using XML format, for further processing.
  - Factory operators can review and process incoming orders, either approving or rejecting them. Automatic email notifications are sent to customers regarding their order status.
- Raw Material Procurement:
  - The factory interacts with raw material distributors through RMI technology. Distributors have their own applications where they can provide details about their company and the raw materials they offer.
- Messaging and Broadcasting:
  - Factory operators can compose promotional messages that are multicast to all customers, enhancing customer engagement and product awareness.


## Technologies Used

- Programming Language: Java
- Database: Redis for data storage.
- Messaging System: RabbitMQ for message queuing.
- Communication Protocols: 
  - RMI (Remote Method Invocation) for communication between factory and distributors.
  - REST (Representational State Transfer) for communication between factory and customers.
  - Socket and Secure Socket (SSL) for communication between factory operators and order processing.
