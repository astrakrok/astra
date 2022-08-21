import {getInnerText} from "../../handler/html.handler";
import {getTrimmedLength, removeWhitespaces} from "../../handler/string.handler";

export const validateTest = test => {
    const errors = {};
    let hasError = false;
    const questionTrimmedLength = getTrimmedLength(test.question);
    if (!questionTrimmedLength || questionTrimmedLength < 10) {
        errors.question = ["Питання повинно містити щонайменше 10 символів"];
        hasError = true;
    }
    const commentText = removeWhitespaces(getInnerText(test.comment));
    const commentTrimmedLength = getTrimmedLength(commentText);
    if (!commentTrimmedLength || commentTrimmedLength < 10) {
        errors.comment = ["Коментар повинен містити щонайменше 10 символів"];
        hasError = true;
    }
    errors.variants = [];
    for (const variant of test.variants) {
        const result = validateTestVariant(variant);
        if (result.hasError) {
            hasError = true;
        }
        errors.variants.push(result.errors);
    }

    const correctVariant = findCorrectVariant(test.variants);
    if (!correctVariant) {
        errors.variantsCorrectness = ["Тест повинен мати щонайменше один правильний варіант"];
        hasError = true;
    }

    if (test.subjects.length < 1) {
        errors.subjects = ["Тест повинен мати щонайменше один предмет"];
        hasError = true;
    }

    return {
        hasError,
        errors
    };
}

const validateTestVariant = variant => {
    const errors = {};
    let hasError = false;
    const titleTrimmedLength = getTrimmedLength(variant.title);
    if (!titleTrimmedLength || titleTrimmedLength < 1) {
        errors.title = ["Варіант повинен містити щонайменше 1 символ"];
        hasError = true;
    }
    const explanationText = removeWhitespaces(getInnerText(variant.explanation));
    const explanationTrimmedLength = getTrimmedLength(explanationText);
    if (!explanationTrimmedLength || explanationTrimmedLength < 10) {
        errors.explanation = ["Пояснення повинно містити щонайменше 10 символів"];
        hasError = true;
    }
    return {
        hasError,
        errors
    };
}

const findCorrectVariant = variants => variants.find(item => item.isCorrect);
