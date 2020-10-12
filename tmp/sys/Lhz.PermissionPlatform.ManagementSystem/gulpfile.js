var gulp = require('gulp');
var del = require('del');
var gts = require('gulp-typescript');
var tscConfig = gts.createProject('tsconfig.json');

gulp.task('clean', function () {
    return del('build/**/*');
});

gulp.task('tsc', ['clean'], function () {
    return gulp.src('src/app/**/*.ts')
        .pipe(gts(tscConfig))
        .pipe(gulp.dest('build/app'));
});

gulp.task('tsc:w', ['tsc'], function () {
    return gulp.watch(['src/**/*.ts', 'src/**/*.html', 'src/**/*.css'], ['default']);
});

gulp.task('copy:lib', ['clean'], function () {
    return gulp.src([
        'node_modules/angular2/bundles/angular2-polyfills.js',
        'node_modules/systemjs/dist/system.src.js',
        'node_modules/rxjs/bundles/Rx.js',
        'node_modules/angular2/bundles/angular2.dev.js',
        'node_modules/angular2/bundles/http.js',
        'node_modules/angular2/bundles/router.dev.js',
        'node_modules/moment/moment.js',
        'node_modules/ng2-bootstrap/bundles/ng2-bootstrap.min.js',
        'node_modules/ng2-modal/Modal.js'
    ]).pipe(gulp.dest('build/lib'));
});

gulp.task('copy:jquery', ['clean'], function () {
    return gulp.src(['node_modules/jquery/dist/**/*.*']).pipe(gulp.dest('build/lib/jquery'));
});

gulp.task('copy:bootstrap', ['clean'], function () {
    return gulp.src(['node_modules/bootstrap/dist/**/*.*']).pipe(gulp.dest('build/lib/bootstrap'));
});

gulp.task('copy:template', ['clean'], function () {
    return gulp.src('src/**/*.html')
        .pipe(gulp.dest('build'));
});

gulp.task('copy:asset', ['clean'], function () {
    return gulp.src('src/**/*.css')
        .pipe(gulp.dest('build'));
});

gulp.task('default', ['clean', 'copy:lib', 'copy:jquery', 'copy:bootstrap', 'copy:template', 'copy:asset', 'tsc', 'tsc:w']);