export const toSelectValue = item => (item ? {
    value: item.id,
    label: item.step.title + " | " + item.title
} : null);
