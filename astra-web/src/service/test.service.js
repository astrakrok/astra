import {variants} from "../data/mock/variants";
import {tests} from "../data/mock/tests";
import {getRandomSubArray} from "../handler/array.handler";
import {specializations} from "../data/mock/specializations";
import {exams} from "../data/mock/exams";
import {route} from "../constant/app.route";
import axios from "axios";

export const getDetailedTests = async () => {
    return tests.map(getDetailedTest);
}

export const saveTest = async test => {
    const response = await axios.post(route.tests, test);
    return response.data;
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
