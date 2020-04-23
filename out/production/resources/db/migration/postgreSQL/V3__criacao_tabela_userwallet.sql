create table WM01B_USER_WALLET(
    WM01B_ID serial,
    WM01B_ID_USER bigint not null,
    WM01B_ID_WALLET bigint not null,
    primary key (WM01B_ID),
    foreign key (WM01B_ID_USER) references  US01_USER(US01_ID),
    foreign key (WM01B_ID_WALLET) references  WA01_WALLET(WA01_ID)
)