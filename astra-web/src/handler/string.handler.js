export const getTrimmedLength = str => str ? str.trim().length : null;

export const removeWhitespaces = str => str.replace(/^\s+|\s+$/gm, '');
