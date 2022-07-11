const withTitle = (
    Component,
    title,
) => {
    return props => {
        document.title = title;

        return (
            <Component {...props} />
        );
    };
}

export default withTitle;
