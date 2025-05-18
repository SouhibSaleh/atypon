from flask import Flask, request, jsonify
from flask_cors import CORS  # Import CORS
from pymongo import MongoClient
import os

print("Testing Flask App...")

app = Flask(__name__)
CORS(app)

mongo_uri = os.getenv("MONGO_URI", "mongodb://mongodb:27017/mydatabase")
client = MongoClient(mongo_uri)
db = client.mydatabase
collection = db.users

sample_users = [
    {"username": "user1", "password": "password1"},
    {"username": "user2", "password": "password2"},
    {"username": "user3", "password": "password3"},
    {"username": "user4", "password": "password4"},
    {"username": "admin", "password": "admin", "grade": 100}
]

if collection.count_documents({}) == 0:
    collection.insert_many(sample_users)


@app.route("/users", methods=["POST"])
def get_users():
    data = request.json
    username = data.get("username")
    password = data.get("password")
    if username != "admin" and password != "admin":
        return jsonify("{}")
    users = list(collection.find({}, {"_id": 0}))
    return jsonify(users)


if __name__ == "__main__":
    print("Starting Flask server on port 5000...")
    app.run(debug=True, host="0.0.0.0", port=5000)
