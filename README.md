# VocaVA - Vocabulary Virtual Assistant

### Overview
Building a virtual assistant to help you learning vocabulary effectively based on your daily usage of English.

This product was achieved top 5 in AI4VN Hackathon 2019.


[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/jaAswzgGPnQ/0.jpg)](http://www.youtube.com/watch?v=jaAswzgGPnQ)

### Service
Main components:
* ocr
* task generator
* speech to text


### Installation


Install tesseract:
```
brew install tesseract
```

Install mongodb:
```
brew install mongodb
```

Start mongodb service
```
brew services start mongodb
```

Install python package:
```
pip3 install -r requirements.txt
```


### Usage
Init database
```
python3 -m server.databases.init_databases
```
Run server:
```
./run-dev
```

### API Guide
```IP_ADDRESS``` is your IP_ADDRESS in your lan. example : ```192.168.1.23``` check by ```ifconfig```, to connect from other device you need to  turn off firewall of server machine and connect to same network (wifi or lan)
Test server on local machine, you can use ```localhost``` or ```0.0.0.0``` instead.
#### Home 
```
http://IP_address:3000/
```

#### Get dictionary

Get all words in dictionary
```
/api/dictionary
```
full api example:
```
http://IP_ADDRESS:3000/api/dictionary
```

#### Get UserInfo
Get user info by user-id, for example user-id = 1
```
/api/user-info?id=1
```

#### Get Oxford Result with specific word
```
/api/get-oxford-result?word=hello
```
#### Get Exam for specific user
```
/api/create-new-exam?user-id=1
```

#### Get specific task for specific word
6 task: definition, definition-task, pronunciation-task, sentence-task, puzzle-task, practice-task

##### Definition
```
/api/create-definition?word=quiz
```

example result (JSON):
```
{
  "antonyms": [], 
  "shortDefinition": "test of knowledge", 
  "synonyms": [
    "test", 
    "quiz"
  ], 
  "type": "definition", 
  "word": "quiz"
}
```


##### Definition task
```
/api/create-definition-task?word=quiz
```

example result (JSON):
```
{
  "answer": 1, 
  "options": [
    "dock", 
    "quiz", 
    "grid", 
    "flaw"
  ], 
  "question": "test of knowledge", 
  "type": "definition_task", 
  "word": "quiz"
}
```


##### Pronunciation task
```
/api/create-pronunciation-task?word=quiz
```

example result (JSON):
```
{
  "answer": 2, 
  "explainAnswer": "kwɪz", 
  "options": [
    "explicitly", 
    "canal", 
    "quiz", 
    "flourish"
  ], 
  "question": "http://audio.oxforddictionaries.com/en/mp3/quiz_gb_1.mp3", 
  "type": "pronunciation_task", 
  "word": "quiz"
}
```

##### Sentence task
```
/api/create-sentence-task?word=quiz
```

example result (JSON):
```
{
  "answer": 0, 
  "options": [
    "quiz", 
    "bishop", 
    "tighten", 
    "flaw"
  ], 
  "question": "a sports ___ ", 
  "type": "sentence_task", 
  "word": "quiz"
}
```

##### Puzzle task
```
/api/create-puzzle-task?word=quiz
```

example result (JSON):
```
{
  "answer": "quiz", 
  "options": [
    "i", 
    "z", 
    "q", 
    "u"
  ], 
  "question": "http://audio.oxforddictionaries.com/en/mp3/quiz_gb_1.mp3", 
  "type": "puzzle_task", 
  "word": "quiz"
}
```


##### Practice task
```
/api/create-practice-task?word=quiz
```
...



