import random

def selectCodeaholic(names):
    lucky_name = random.choice(names)
    names.remove(lucky_name)
    return lucky_name
    
def selectQuestions(questions):
    lucky_questions = []
    for i in range(0,5):
        qt = random.choice(questions)
        lucky_questions.append(qt)
        questions.remove(qt)
    return lucky_questions

def runSelection():
    names = ["David","Jheison","Fabian","Andres"]
    questions = range(1, 21)
    while len(names) > 0:        
        lucky_name = selectCodeaholic(names)
        print(lucky_name)
        his_questions = selectQuestions(questions)
        print(his_questions)

        
 
