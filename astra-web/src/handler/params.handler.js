export const mergeSearchParams = (searchParams, otherParams) => {
    const params = {};
    for (const entry of searchParams.entries()) {
        const [param, value] = entry;
        params[param] = value;
    }
    return {
        ...params,
        ...otherParams
    };
}
