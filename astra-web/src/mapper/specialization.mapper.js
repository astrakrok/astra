export const toSelectValue = item => ({
    value: item.id,
    label: item.step.title + " | " + item.title
});
