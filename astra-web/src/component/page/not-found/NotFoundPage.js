import withTitle from "../../hoc/withTitle/withTitle";

const NotFoundPage = () => {
    return (
        <div className="container">
            <div className="row">
                Page not found
            </div>
        </div>
    );
}

export default withTitle(NotFoundPage, "Сторінку не знайдено");
