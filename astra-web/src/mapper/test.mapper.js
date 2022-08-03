export const mapTrainingToNavigationItem = (test, selected = false) => {
    return {
        selected,
        status: getTestStatus(test)
    }
}

export const mapExaminationToNavigationItem = (test, selected = false) => {
    return {
        selected,
        status: (test.status === "pending" || test.status === "failed" || test.status === "processed") ? test.status : getStatusByUserAnswer()
    }
}

const getStatusByUserAnswer = userAnswer => {
    return userAnswer == null ? "" : "processed";
}

const getTestStatus = test => {
    if (test.userAnswer == null) {
        return "";
    }
    const correctVariantId = getCorrectVariant(test.variants);
    return test.userAnswer === correctVariantId ? "correct" : "wrong";
}

const getCorrectVariant = variants => {
    return variants.find(item => item.isCorrect).id;
}
