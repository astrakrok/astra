import {variants} from "../mock/data/variants";
import {tests} from "../mock/data/tests";
import {getRandomSubArray} from "../handler/array.handler";
import {specializations} from "../mock/data/specializations";
import {exams} from "../mock/data/exams";

export const getDetailedTests = async () => {
    return tests.map(getDetailedTest);
}

const getDetailedTest = test => {
    const variants = getVariantsByTestId(test.id);
    return {
        ...test,
        variants,
        specializations: getRandomSubArray(specializations, 1),
        exams: getRandomSubArray(exams, 1)
    };
}

const getVariantsByTestId = testId => {
    return variants.filter(item => item.testId === testId);
}
