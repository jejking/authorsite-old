<?php
DEFINE('BOOKS_PRODUCED_QUERY', 'select workproducertype, count(wwp.work_id) as theCount from book b, work_workproducers wwp where wwp.abstractHuman_Id = ? and wwp.work_id = b.id group by workproducertype');
DEFINE('ARTICLES_PRODUCED_QUERY', 'select workproducertype, count(wwp.work_id) as theCount from article a, work_workproducers wwp where wwp.abstractHuman_Id = ? and wwp.work_id = a.id group by workproducertype');
DEFINE('THESES_PRODUCED_QUERY', 'select workproducertype, count(wwp.work_id) as theCount from thesis t, work_workproducers wwp where wwp.abstractHuman_Id = ? and wwp.work_id = t.id group by workproducertype');
DEFINE('CHAPTERS_PRODUCED_QUERY', 'select workproducertype, count(wwp.work_id) as theCount from chapter ch, work_workproducers wwp where wwp.abstractHuman_Id = ? and wwp.work_id = ch.id group by workproducertype');
DEFINE('WEB_RESOURCES_PRODUCED_QUERY', 'select workproducertype, count(wwp.work_id) as theCount from webresource wr, work_workproducers wwp where wwp.abstractHuman_Id = ? and wwp.work_id = wr.id group by workproducertype;');
?>
