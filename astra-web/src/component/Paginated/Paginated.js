import {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {app} from "../../constant/app";
import LoaderBoundary from "../LoaderBoundary/LoaderBoundary";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import Pagination from "../Pagination/Pagination";
import "./Paginated.css";

const Paginated = ({
                       children = () => {
                       },
                       pageHandler = () => {
                       },
                       pageSize = app.pageSize
                   }) => {
    const [searchParams, setSearchParams] = useSearchParams();
    const [page, setPage] = useState({
        items: null,
        pagesCount: 1
    });

    const pageNumber = searchParams.get("page") * 1 || 1;

    useEffect(() => {
        const fetchPage = async () => {
            setPage(previous => ({
                ...previous,
                items: null
            }));
            const page = await pageHandler(pageSize, pageNumber);
            setPage(page);
        }

        fetchPage();
    }, []);

    const changePage = newPageNumber => {
        if (pageNumber > page.totalPages || pageNumber < 1) {
            return;
        }
        setSearchParams({
            page: newPageNumber
        });
    }

    return (
        <div className="Paginated">
            <div className="s-vflex">
                <LoaderBoundary condition={page.items == null} className="s-hflex-center">
                    {
                        page.items ? children(page.items) : null
                    }
                </LoaderBoundary>
                <DisplayBoundary condition={page.totalPages > 1}>
                    <div className="s-hflex-center">
                        <Pagination
                            pagesCount={page.totalPages}
                            currentPage={pageNumber}
                            onPreviousPageClicked={() => changePage(pageNumber - 1)}
                            onPageClicked={page => changePage(page)}
                            onNextPageClicked={() => changePage(pageNumber + 1)}/>
                    </div>
                </DisplayBoundary>
            </div>
        </div>
    );
}

export default Paginated;
