import ollama

#ollama serve를 통해서 진입 설정이 없다면 127.0.0.1:11434로 설정됨
def baseuse():
    response = ollama.generate(
        model="llama3",
        prompt="/bye"
    )
    print(response["response"])

def chatAPI():
    response = ollama.chat(
        model="llama3",
        messages=[
            {
                "role" : "user",
                "content" : "I connected to you again using Python. What do you think is different from before?"
            }
        ]
    )
    print(response["message"]["content"])

#한 단어씩 끊어서 출력해줌
def streaming():
    stream = ollama.generate(
        model="llama3",
        prompt="AI에 대한 시를 작성해주세요.",
        stream=True
    )

    for chunk in stream:
        print(chunk["response"], end="", flush=True)

streaming()