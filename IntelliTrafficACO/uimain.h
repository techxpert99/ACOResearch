#ifndef UIMAIN_H
#define UIMAIN_H

#include <QMainWindow>
#include <QScreen>

QT_BEGIN_NAMESPACE
namespace Ui { class UIMain; }
QT_END_NAMESPACE

class UIMain : public QMainWindow
{
    Q_OBJECT

public:
    UIMain(QWidget *parent = nullptr);
    ~UIMain();

private:
    Ui::UIMain *ui;
};
#endif // UIMAIN_H
