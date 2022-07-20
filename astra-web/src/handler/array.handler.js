import {generate} from "./random.handler";

export const shuffle = array => {
    return [...array].sort(() => Math.random() - 0.5);
}

export const take = (array, n) => {
    return array.slice(0, n);
}

export const getRandomSubArray = (array, minLength = 0) => {
    const randomCount = generate(minLength, array.length);
    return take(shuffle(array), randomCount);
}
