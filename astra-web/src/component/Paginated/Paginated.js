import {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {app} from "../../constant/app";
import LoaderBoundary from "../LoaderBoundary/LoaderBoundary";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import Pagination from "../Pagination/Pagination";
import SingleSelect from "../SingleSelect/SingleSelect";
import "./Paginated.css";
import {mergeSearchParams} from "../../handler/params.handler";
import useDidChange from "../../hook/useDidChange";
import Spacer from "../Spacer/Spacer";

// backend has no "unlimited" page size, so "all" is requested as one huge page
const allItemsRequestSize = 2147483647;

const toPageSizeOption = size => ({
    label: size === app.pageSizeAll ? "Усі" : `${size}`,
    value: size
});

const getInitialPageSize = (searchParams, pageSizeOptions, defaultPageSize) => {
    if (!pageSizeOptions) {
        return defaultPageSize;
    }
    const param = searchParams.get("pageSize");
    const size = param === app.pageSizeAll ? param : param * 1;
    return pageSizeOptions.includes(size) ? size : defaultPageSize;
}

const Paginated = ({
    children = {
        content: () => {},
        filter: () => {}
    },
    initialFilter = {},
    pageHandler = () => {},
    pageSize: defaultPageSize = app.pageSize,
    pageSizeOptions = null
}) => {
    const [searchParams, setSearchParams] = useSearchParams();
    const [page, setPage] = useState({
        items: null,
        pagesCount: 1
    });
    const [filter, setFilter] = useState(initialFilter);
    const [pageSize, setPageSize] = useState(() => getInitialPageSize(searchParams, pageSizeOptions, defaultPageSize));

    const isAllItems = pageSize === app.pageSizeAll;
    const pageNumber = isAllItems ? 1 : searchParams.get("page") * 1 || 1;

    const fetchPage = async pageNumber => {
        setPage(previous => ({
            ...previous,
            items: null
        }));
        const page = await pageHandler(filter, {
            pageSize: isAllItems ? allItemsRequestSize : pageSize,
            pageNumber: (pageNumber ? pageNumber : searchParams.get("page")*1) - 1
        });
        setPage(page);
    }

    useEffect(() => {
        changePage(pageNumber);
    }, []);

    useDidChange(() => {
        changePage(1);
    }, [filter, pageSize]);

    const changePage = async newPageNumber => {
        if (pageNumber > page.totalPages || pageNumber < 1) {
            return;
        }
        await fetchPage(newPageNumber);
        const pageSizeParam = pageSizeOptions ? {pageSize} : {};
        setSearchParams(mergeSearchParams(searchParams, {page: newPageNumber, ...pageSizeParam}));
    }

    return (
        <div className="Paginated full-width">
            {
                children.filter({
                    initialFilter,
                    setFilter: filter => {
                        setFilter(filter)
                    }
                })
            }
            <DisplayBoundary condition={pageSizeOptions != null}>
                <Spacer height={20} />
                <div className="s-hflex-end page-size">
                    <span className="page-size-label">На сторінці:</span>
                    <Spacer width={10} />
                    <SingleSelect
                        className="page-size-select"
                        isSearchable={false}
                        value={toPageSizeOption(pageSize)}
                        onChange={option => setPageSize(option.value)}
                        options={(pageSizeOptions || []).map(toPageSizeOption)}/>
                </div>
            </DisplayBoundary>
            <Spacer height={20} />
            <div className="s-vflex">
                <LoaderBoundary condition={page.items == null} className="s-hflex-center loader">
                    {
                        page.items ? children.content({
                            items: page.items,
                            refreshPage: fetchPage,
                            orderFrom: isAllItems ? 0 : (pageNumber - 1) * pageSize
                        }) : null
                    }
                </LoaderBoundary>
                <DisplayBoundary condition={page.pagesCount > 1}>
                    <div className="s-hflex-center">
                        <Pagination
                            pagesCount={page.pagesCount}
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
