var Flow = (function () {

    // 替换模板中，用中括号包含的变量，例如{abc}
    function format(string, obj) {
        return string.replace(/\{([0-9A-Za-z_]+)\}/g, function (m, i) {
            return obj[i];
        });
    }

    // 将数组中的元素相加, [start, end]
    function sumarray(array, start, end) {
        var sum = 0;
        if (end >= start) {
            for (var i = start; i <= end; i++) {
                sum += array[i];
            }
        }
        return sum;
    }

    // 画一个外围的方框
    function createWrapRectPath(rect) {
        var padding = 5;
        var pos = {
            x: rect.x - padding,
            y: rect.y - padding,
            x2: rect.x2 + padding,
            y2: rect.y2 + padding + 2
        };
        return format("M{x},{y} L{x2},{y} L{x2},{y2} L{x},{y2} Z", pos);
    }

    // 画直线和箭头
    function drawLineArrow(x1, y1, x2, y2) {
        var path;
        var slopy, cosy, siny;
        var Par = 10.0;
        var x3, y3;
        slopy = Math.atan2((y1 - y2), (x1 - x2));
        cosy = Math.cos(slopy);
        siny = Math.sin(slopy);

        path = "M" + x1 + "," + y1 + " L" + x2 + "," + y2;

        x3 = x2;
        y3 = y2;

        path += " M" + x3 + "," + y3;
        path += " L" + (Number(x3) + Number(Par * cosy - (Par / 2.0 * siny))) + "," + (Number(y3) + Number(Par * siny + (Par / 2.0 * cosy)));
        path += " M" + (Number(x3) + Number(Par * cosy + Par / 2.0 * siny) + "," + (Number(y3) - Number(Par / 2.0 * cosy - Par * siny)));
        path += " L" + x3 + "," + y3;

        console.log("path=" + path);
        return path;
    }

    // 画折线和箭头
    function drawBrokenLineArrow(points) {
        var path = "";
        for (var i = 0; i <= points.length - 2; i++) {
            var p = points[i];
            var p2 = points[i + 1];
            if (i === points.length - 2) {
                path += drawLineArrow(p[0], p[1], p2[0], p2[1]);
            } else {
                path += "M" + p[0] + "," + p[1] + " L" + p2[0] + "," + p2[1];
            }
        }

        console.log("BrokenLine, path=" + path);

        return path;
    }

    // 判断是否是数字
    function isNumber(value) {
        return typeof value === 'number';
    }

    function assertIsNumber(value){
        if(! isNumber(value)){
            throw new Error("非法数字");
        }
    }

    function Rect(x, y, w, h) {
        assertIsNumber(x);
        assertIsNumber(y);
        assertIsNumber(w);
        assertIsNumber(h);

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.x2 = x + w;
        this.y2 = y + h;
        this.cx = x + w / 2;
        this.cy = y + h / 2;
    }

    function createRectByConfig(config) {

        var w = config.w;
        var h = config.h;

        var x = isNumber(config.cx) ? config.cx - w / 2 : config.x;
        var y = isNumber(config.cy) ? config.cy - h / 2 : config.y;

        if (!isNumber(x)) {
            x = 0;
        }

        if (!isNumber(y)) {
            y = 0;
        }

        return new Rect(x, y, w, h);

    }

    var __extends = (this && this.__extends) || function (d, b) {
            for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
            function __() {
                this.constructor = d;
            }

            __.prototype = b.prototype;
            d.prototype = new __();
        };

    var Config = {
        // 字体
        FontSize: 14,
        // 颜色
        NodeColor: "#0AAAFF",
        NodeTextColor : "#FFF",
        NodeHighlightColor: "#F00",
        LineTextColor: '#015687',
        // 节点文本偏移,为了使文本对齐
        NodeTextOffset: 4,
        // 节点默认高度
        NodeHeight: 32,
        DecisionNodeHeight: 64,
        // 线条
        LineWidth : 1,
        HighlightLineWidth : 2
    };

    Config.FontSizePx = Config.FontSize + "px";

    function Line(snap, n1, n2, config) {
        this.snap = snap;
        this.n1 = n1;
        this.n2 = n2;
        this.config = config;
    }

    Line.prototype.render = function () {

        var rect1 = this.n1.getRect();
        var rect2 = this.n2.getRect();

        if (this.config.direction) {

            var points = [];

            // 渲染折线
            var direction = this.config.direction;

            var inRight = rect2.cx > rect1.cx;
            var inBottom = rect2.cy > rect1.cy;

            if (direction === "x") {

                var p1 = [inRight ? rect1.x2 : rect1.x, rect1.cy];
                var p2 = [rect2.cx, inBottom ? rect2.y : rect2.y2];
                points = [
                    p1,
                    [p2[0], p1[1]],
                    p2
                ];

            } else if (direction === 'y') {

                var p1 = [rect1.cx, inBottom ? rect1.y2 : rect1.y];
                var p2 = [inRight ? rect2.x : rect2.x2, rect2.cy];
                points = [
                    p1,
                    [p1[0], p2[1]],
                    p2
                ];

            } else {
                throw new Error("未知的参数,direction=" + direction);
            }
            var path1 = drawBrokenLineArrow(points);
            this.snap.paper.path(path1).attr({
                stroke: Config.NodeColor,
                strokeWidth: Config.LineWidth
            });

            if (this.config.text) {
                var firstPoint = points[0];
                this.snap.text(firstPoint[0] + 5, firstPoint[1] + Config.FontSize, this.config.text).attr({
                    fill: Config.LineTextColor,
                    fontSize: Config.FontSizePx
                });
            }

        } else {
            // 渲染直线
            var path1 = drawLineArrow(rect1.cx, rect1.y2, rect2.cx, rect2.y);
            this.snap.paper.path(path1).attr({
                stroke: Config.NodeColor,
                strokeWidth: Config.LineWidth
            });

            if (this.config.text) {
                this.snap.text(rect1.cx + 5, rect1.y2 + Config.FontSize, this.config.text).attr({
                    fill: Config.LineTextColor,
                    fontSize: Config.FontSizePx
                });
            }

        }

        return this;
    };


    var BaseNode = (function () {
        function BaseNode(snap, config) {
            this.snap = snap;
            this.config = config;
            this.id = config.id;
            this.rect = createRectByConfig(config);
        }

        BaseNode.prototype.getRect = function () {
            return this.rect;
        };

        BaseNode.prototype.updateXY = function (x, y) {
            this.rect = new Rect(x, y, this.rect.w, this.rect.h);
        };

        BaseNode.prototype.getId = function () {
            return this.id;
        };

        return BaseNode;
    })();


    var StartNode = (function (_super) {
        __extends(StartNode, _super);

        function StartNode(snap, config) {
            config.w = config.w || 80;
            config.h = config.h || Config.NodeHeight;
            config.id = config.id || "start";

            _super.call(this, snap, config);
        }

        StartNode.prototype.render = function () {

            var x = this.rect.x;
            var y = this.rect.y;
            var w = this.rect.w;
            var h = this.rect.h;

            this.snap.rect(x, y, w, h, h / 2).attr({
                fill: Config.NodeColor
            });

            var fontH = Config.FontSize;

            // 文本居中对齐
            this.snap.text(x + (w - fontH * 2) / 2, y + (h - fontH) / 2 + fontH / 2 + Config.NodeTextOffset, "开始").attr({
                fill: Config.NodeTextColor,
                fontSize: Config.FontSizePx
            });

            return this;
        };

        return StartNode;
    })(BaseNode);


    var EndNode = (function (_super) {
        __extends(EndNode, _super);

        function EndNode(snap, config) {
            config.w = config.w || 80;
            config.h = config.h || Config.NodeHeight;
            config.id = config.id || "end";

            _super.call(this, snap, config);
        }

        EndNode.prototype.render = function () {

            var x = this.rect.x;
            var y = this.rect.y;
            var w = this.rect.w;
            var h = this.rect.h;

            this.snap.rect(x, y, w, h, h / 2).attr({
                fill: Config.NodeColor
            });

            var fontH = Config.FontSize;

            // 文本居中对齐
            this.snap.text(x + (w - fontH * 2) / 2, y + (h - fontH) / 2 + fontH / 2 + Config.NodeTextOffset, "结束").attr({
                fill: Config.NodeTextColor,
                fontSize: Config.FontSizePx
            });

            return this;

        };

        return EndNode;
    })(BaseNode);


    var TaskNode = (function (_super) {
        __extends(TaskNode, _super);

        function TaskNode(snap, config) {
            config.w = config.w || 100;
            config.h = config.h || Config.NodeHeight;
            _super.call(this, snap, config);
        }

        TaskNode.prototype.render = function () {

            var x = this.rect.x;
            var y = this.rect.y;
            var w = this.rect.w;
            var h = this.rect.h;

            this.snap.rect(x, y, w, h, 5).attr({
                fill: Config.NodeColor
            });

            if (this.config.highlight) {
                var wrappath = createWrapRectPath(this.getRect());

                this.snap.paper.path(wrappath).attr({
                    fill: 'none',
                    stroke: Config.NodeHighlightColor,
                    strokeWidth: Config.HighlightLineWidth
                });

            }

            var fontH = Config.FontSize;
            var fontCount = this.config.name.length;

            // // 文本居中对齐
            this.snap.text(x + (w - fontH * fontCount) / 2, y + (h - fontH) / 2 + fontH / 2 + Config.NodeTextOffset, this.config.name).attr({
                fill: Config.NodeTextColor,
                fontSize: Config.FontSizePx
            });

            return this;

        };

        return TaskNode;
    })(BaseNode);


    var DecisionNode = (function (_super) {
        __extends(DecisionNode, _super);

        function DecisionNode(snap, config) {
            config.w = config.w || 150;
            config.h = config.h || Config.DecisionNodeHeight;
            _super.call(this, snap, config);
        }

        DecisionNode.prototype.render = function () {

            var x = this.rect.x;
            var y = this.rect.y;
            var w = this.rect.w;
            var h = this.rect.h;
            var cx = this.rect.cx;
            var cy = this.rect.cy;
            var x2 = this.rect.x2;
            var y2 = this.rect.y2;

            this.snap.paper.polygon([x, cy, cx, y, x2, cy, cx, y2]).attr({
                fill: Config.NodeColor
            });

            if (this.config.highlight) {
                var wrappath = createWrapRectPath(this.getRect());

                this.snap.paper.path(wrappath).attr({
                    fill: 'none',
                    stroke: Config.NodeHighlightColor,
                    strokeWidth: Config.HighlightLineWidth
                });

            }

            var fontH = Config.FontSize;
            var fontCount = this.config.name.length;

            // // 文本居中对齐
            this.snap.text(x + (w - fontH * fontCount) / 2, y + (h - fontH) / 2 + fontH / 2 + Config.NodeTextOffset, this.config.name).attr({
                fill: Config.NodeTextColor,
                fontSize: Config.FontSizePx
            });

            return this;

        };

        return DecisionNode;
    })(BaseNode);


    function Flow(id, diagramData) {
        this.snap = Snap("#" + id);
        this.id = id;
        this.diagramData = diagramData;
        this.nodeMap = {};
        this.nodes = [];
    }

    Flow.prototype._registerNode = function (node) {
        var id = node.getId();
        console.log("_registerNode, id=" + id);

        if (!id) {
            throw new Error("id is null");
        }

        if (!node) {
            throw new Error("node is null, id=" + id);
        }

        this.nodeMap[id] = node;

    }

    Flow.prototype.findNode = function (id) {
        if (!id) {
            throw new Error("id is null");
        }

        var node = this.nodeMap[id];
        if (!node) {
            throw new Error("找不到node, id=" + id);
        }
        return node;
    }

    Flow.prototype._newNode = function (node) {
        if (node.type === 'start') {
            return new StartNode(this.snap, node);
        } else if (node.type === 'task') {
            return new TaskNode(this.snap, node);
        } else if (node.type === 'decision') {
            return new DecisionNode(this.snap, node);
        } else if (node.type === 'end') {
            return new EndNode(this.snap, node);
        }

        throw new Error("非法Node,type=" + node.type);
    }

    Flow.prototype.render = function (useTableLayout) {

        if (useTableLayout) {

            var nodes = [];
            var cellXDistances = [];
            var cellYDistances = [];

            for (var i = 0; i < this.diagramData.nodes.length; i++) {
                var nodeConfig = this.diagramData.nodes[i];
                var node = this._newNode(nodeConfig);
                this._registerNode(node);
                nodes.push(node);

                if (nodeConfig.cell) {
                    var cellItems = nodeConfig.cell.split(",");
                    var row = parseInt(cellItems[0]);
                    var col = parseInt(cellItems[1]);
                    var rect = node.getRect();

                    if (!isNumber(cellYDistances[row])) {
                        cellYDistances[row] = 0;
                    }
                    cellYDistances[row] = Math.max(cellYDistances[row], rect.h);

                    if (!isNumber(cellXDistances[col])) {
                        cellXDistances[col] = 0;
                    }
                    cellXDistances[col] = Math.max(cellXDistances[col], rect.w);
                } else {
                    throw new Error("非法Node, 没有设置cell");
                }
            }

            console.log("column widths: ");
            console.log(cellXDistances);
            console.log("row widths: ");
            console.log(cellYDistances);

            var data = this.diagramData;

            for (var i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                var cell = node.config.cell;
                var cellItems = cell.split(",");
                var row = parseInt(cellItems[0]);
                var col = parseInt(cellItems[1]);

                var newCx = sumarray(cellXDistances, 0, col - 1) + cellXDistances[col] / 2 + data.tableMargin + data.cellPadding * 2 * col + data.cellPadding;
                var newCy = sumarray(cellYDistances, 0, row - 1) + cellYDistances[row] / 2 + data.tableMargin + data.cellPadding * 2 * row + data.cellPadding;

                console.log("cell=" + cell + ", newCx=" + newCx + ",newCy=" + newCy);

                var rect = node.getRect();
                node.updateXY(newCx - rect.w / 2, newCy - rect.h / 2);

                node.render();
            }

            var svgMaxX = sumarray(cellXDistances, 0, cellXDistances.length - 1) + data.tableMargin * 2 + data.cellPadding * 2 * cellXDistances.length;
            var svgMaxY = sumarray(cellYDistances, 0, cellYDistances.length - 1) + data.tableMargin * 2 + data.cellPadding * 2 * cellYDistances.length;

            console.log("svgMaxX=" + svgMaxX + ", svgMaxY=" + svgMaxY);

            this.snap.paper.attr({
                width: svgMaxX + "px",
                height: svgMaxY + "px"
            });

        } else {
            for (var i = 0; i < this.diagramData.nodes.length; i++) {
                var nodeConfig = this.diagramData.nodes[i];
                var node = this._newNode(nodeConfig);
                node.render();
                this._registerNode(node);
            }
        }

        for (var i = 0; i < this.diagramData.lines.length; i++) {
            var line = this.diagramData.lines[i];
            new Line(this.snap, this.findNode(line.from), this.findNode(line.to), line).render();
        }
    };

    Flow.prototype.renderByTableLayout = function () {
        this.render(true);
    };

    return Flow;

})();

