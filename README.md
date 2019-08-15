# vocava


### Service
Gồm 3 phần:
* speech to text
* ocr
* task generator


### Requirements

Install mongodb, for unix user:
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
or
```
python3 -m pip install -r requirements.txt
```

### Instruction to run
Init database
```
python3 -m server.databases.init_databases
```
Run server:
```
./run-dev
```

### API Guide
```IP_ADDRESS``` is your IP_ADDRESS in your lan. example : ```192.168.1.23``` check by ```ifconfig```, to connect from other device you need to  turn off firewall of server machine and connect to same network ( wifi or lan )
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
đưa tham số là user-id, hệ thống sẽ phân tích và tạo ra bài kiểm tra là danh sách các task như ở dưới

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
Kết quả trả về bao gồm dạng task, từ , định nghĩa ngắn, từ đồng nghĩa, từ trái nghĩa -> cho người dùng nhìn từ, và định nghĩa của nó, bên cạnh đó cho người dùng xem qua các từ đồng nghĩa có level thấp hơn ( để người dùng có thể hình dung nghĩa của từ )

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
Kết quả trả về bao gồm dạng task, từ , định nghĩa của từ, 4 từ là các câu trả lời trắc nghiệm, và vị trí của đáp án



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
Kết quả trả về bao gồm dạng task, từ , link tới file audio của từ, 4 từ là các câu trả lời trắc nghiệm, và vị trí của đáp án, sau khi người dùng nhập đáp án thì hiện explainAnswer là phát cách đọc của từ theo bang IPA

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
Kết quả trả về bao gồm dạng task, từ , 1 câu về từ cần hỏi, đã thay thế = '\_\_\_', 4 câu trả lời trắc nghiệm, và vị trí của đáp án,
cẩn thận với loại task này vì vẫn còn lỗi chưa khắc phục đc. 1 là do các dạng khác của từ trong câu, vd khi học về từ query nhưng trong câu chỉ có queies thì question ko biến đc từ queies thành ___ . tiếp theo là như cái câu mẫu, câu a sports ____ quá chung chung, ko thể hiện đc tính chất của từ quiz


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
Kết quả trả về bao gồm dạng task, từ , link tới file audio của từ, options là các chữ cái đã bị xáo trộn, answer là từ, khi làm cần kiểm tra xem người dùng viết thứ tự của từ có đúng ko?


##### Practice task
```
/api/create-practice-task?word=quiz
```

random 1 trong 4 cái bài kiểm tra kia để kiểm tra lại



