import random

def selectCodeaholic(names):
    lucky_name = random.choice(names)
    names.remove(lucky_name)
    return lucky_name
    
def selectQuestions(questions,question_per_person):
    lucky_questions = []
    for i in range(0,question_per_person):
        qt = random.choice(questions)
        lucky_questions.append(qt)
        questions.remove(qt)
    return lucky_questions

def runSelection(max_questions):
    names = ["David","Jheison","Fabian","Andres"]
    questions = range(1, (max_questions+1))
    while len(names) > 0:        
        lucky_name = selectCodeaholic(names)
        print(lucky_name)
        his_questions = selectQuestions(questions,1)
        print(his_questions)

        
 
