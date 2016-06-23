require.config({
    paths: {
        jquery: '../commons/js/jquery-1.12.4.min',
        m1Normal:'m1.normal',
        m1Sub:'m1.sub',
        m1Alert:'m1.alert'
    }
});
 
require(['m1Alert','m1Normal'],function(alert,normal) {

});


