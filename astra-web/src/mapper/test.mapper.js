export const mapToNavigationItem = (test, selected = false) => {
    return {
        selected,
        status: getTestStatus(test)
    }
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
