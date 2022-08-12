import {route} from "../constant/app.route";
import {client} from "../shared/js/axios";

const mapDateToSeconds = timestamp => {
    const milliseconds = new Date(timestamp + "Z").getTime();
    return Math.round(milliseconds / 1000);
}

export const start = async data => {
    const response = await client.post(route.examinations, data);
    const result = response.data;
    return {
        ...result,
        finishedAt: mapDateToSeconds(result.finishedAt)
    };
}

export const getResult = (examId, specializationId) => {
    return {
        tests: [
            {
                id: 4,
                question: "sadfasdfasdfa",
                comment: "asdfasdfasdfasdffasdfas",
                userAnswer: 5,
                variants: [
                    {
                        id: 5,
                        testId: 4,
                        title: "asdfasdffas",
                        explanation: "asdfasdfasdfasdfasdf",
                        isCorrect: true
                    }
                ]
            },
            {
               id: 2,
               question: "Перше тестове питання",
               comment: "Коментар до першого тестового питання",
               userAnswer: 1,
               variants: [
                    {
                        id: 2,
                        testId: 2,
                        title: "Варіант 2",
                        explanation: "Пояснення 2",
                        isCorrect:true
                    },
                    {
                        id: 1,
                        testId: 2,
                        title: "Варіант 1",
                        explanation: "Пояснення 1",
                        isCorrect: false
                    }
                ]
            },
            {
                id:6,
                question: "Створення нового валідного тесту",
                comment: "Коментар до даного тесту не буде відображатись у списку тестів",
                userAnswer: 8,
                variants: [
                    {
                        id: 8,
                        testId: 6,
                        title: "Варіант 2",
                        explanation: "... з поясненням",
                        isCorrect: false
                    },
                    {
                        id: 7,
                        testId: 6,
                        title: "Варіант 1",
                        explanation: "І пояснення до нього",
                        isCorrect: true
                    }
                ]
            },
            {
                id: 5,
                question: "sadfasdfasdf",
                comment: "asdfasdfasdfasdfasdf",
                userAnswer: 6,
                variants: [
                    {
                        id: 6,
                        testId: 5,
                        title: "asdfasdfasd",
                        explanation: "asdfasdfasdfasdfasfds",
                        isCorrect: true
                    }
                ]
            },
            {
                id: 7,
                question: "ще один тест",
                comment: "ще один коментар",
                userAnswer: 10,
                variants: [
                    {
                        id: 10,
                        testId: 7,
                        title: "варіант 2",
                        explanation: "пояснення 2",
                        isCorrect: true
                    },
                    {
                        id: 9,
                        testId: 7,
                        title: "варіант 1",
                        explanation: "пояснення 1",
                        isCorrect: false
                    }
                ]
            }
        ],
        correctCount: 3,
        total: 5
    };
}

export const updateAnswer = async (id, data) => {
    const url = route.examinations + "/" + id;
    const response = await client.put(url, data)
        .catch(error => ({
            data: {
                error
            }
        }));
    
    return response.data;
}
